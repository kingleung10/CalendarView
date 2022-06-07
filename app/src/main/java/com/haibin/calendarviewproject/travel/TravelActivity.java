package com.haibin.calendarviewproject.travel;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.haibin.calendarviewproject.R;
import com.haibin.calendarviewproject.base.activity.BaseActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kingleung
 * Created on 2022/6/1.
 */
public class TravelActivity extends BaseActivity implements
        CalendarView.OnCalendarSelectListener,
        CalendarView.OnCalendarLongClickListener,
        CalendarView.OnMonthChangeListener,
        CalendarView.OnYearChangeListener,
        CalendarView.OnWeekChangeListener,
        CalendarView.OnViewChangeListener,
        CalendarView.OnCalendarInterceptListener,
        CalendarView.OnYearViewChangeListener,
        CalendarView.OnTranslationYListener{

    TextView mTextMonthDay;

    CalendarView mCalendarView;
    CalendarView mMultiCalendarView;

    RelativeLayout mRelativeTool;
    CalendarLayout mCalendarLayout;

    private ImageView mTodayView;
    private ImageView mMoreView;

    private Calendar mCalendar;

    volatile boolean toScroll = true;
    volatile boolean isMonthView = true;
    volatile boolean isMulti = false;

    public static void show(Context context) {
        context.startActivity(new Intent(context, TravelActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_travel;
    }

    @Override
    protected void initView() {
        setStatusBarDarkMode();

        mTextMonthDay = findViewById(R.id.tv_month_day);

        mRelativeTool = findViewById(R.id.rl_tool);
        mCalendarView = findViewById(R.id.calendarView);
        mMultiCalendarView = findViewById(R.id.multiCalendarView);

        mTodayView = findViewById(R.id.iv_today);
        mMoreView = findViewById(R.id.iv_more);

        //mCalendarView.setRange(2018, 7, 1, 2019, 4, 28);
        mTextMonthDay.setOnClickListener(v -> {
            if (!mCalendarLayout.isExpand()) {
                mCalendarLayout.expand();
                return;
            }
            mCalendarView.showYearSelectLayout(mCalendar.getYear());
        });
        findViewById(R.id.iv_more).setOnClickListener(v -> {
            if (isMulti) {
                mCalendarView.setVisibility(View.VISIBLE);
                mMultiCalendarView.setVisibility(View.GONE);

                mCalendarLayout.reset(mCalendarView);
            }
            else {
                mCalendarView.setVisibility(View.GONE);
                mMultiCalendarView.setVisibility(View.VISIBLE);
                mCalendarLayout.reset(mMultiCalendarView);
            }
            isMulti = !isMulti;

        });

        mTodayView.setOnClickListener(v -> {
            if (isMulti) {
                if (isMonthView) {
                    mCalendarView.scrollToCurrent();
                } else {
                    if (!mCalendarLayout.isExpand()) {
                        mCalendarLayout.expand();
                    }
                }
            }
            else {
                if (!mCalendarLayout.isExpand()) {
                    mCalendarLayout.expand();
                }
            }
        });

        mCalendarLayout = findViewById(R.id.calendarLayout);
        mCalendarView.setOnYearChangeListener(this);
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnMonthChangeListener(this);
        mCalendarView.setOnCalendarLongClickListener(this, true);
        mCalendarView.setOnWeekChangeListener(this);
        mCalendarView.setOnYearViewChangeListener(this);
        mCalendarView.setOnTranslationYListener(this);

        //设置日期拦截事件，仅适用单选模式，当前无效
        mCalendarView.setOnCalendarInterceptListener(this);

        mCalendarView.setOnViewChangeListener(this);

        mCalendarView.setOnVerticalItemInitialize((viewHolder, position, year, month) -> {
            TextView currentMonthView = viewHolder.itemView.findViewById(com.haibin.calendarview.R.id.current_month_view);
            if (currentMonthView != null) {
                // 显示下个月的标题
                Calendar c = new Calendar();
                c.setYear(year);
                c.setMonth(month);
                c.setDay(1);
                java.util.Calendar cc = c.toCalendar();
                cc.add(java.util.Calendar.MONTH, 1);
                Calendar calendar = Calendar.fromCalendar(cc);
                currentMonthView.setText(calendar.getYear() + "年" + calendar.getMonth() + "月");
            }
        });

        mMultiCalendarView.setOnVerticalItemInitialize((viewHolder, position, year, month) -> {
            TextView currentMonthView = viewHolder.itemView.findViewById(com.haibin.calendarview.R.id.current_month_view);
            if (currentMonthView != null) {
                // 显示下个月的标题
                Calendar c = new Calendar();
                c.setYear(year);
                c.setMonth(month);
                c.setDay(1);
                java.util.Calendar cc = c.toCalendar();
                cc.add(java.util.Calendar.MONTH, 1);
                Calendar calendar = Calendar.fromCalendar(cc);
                currentMonthView.setText(calendar.getYear() + "年" + calendar.getMonth() + "月");
            }
        });
        mMultiCalendarView.setOnYearChangeListener(this);
        mMultiCalendarView.setOnCalendarSelectListener(this);
        mMultiCalendarView.setOnMonthChangeListener(this);
        mMultiCalendarView.setOnCalendarLongClickListener(this, true);
        mMultiCalendarView.setOnWeekChangeListener(this);
        mMultiCalendarView.setOnYearViewChangeListener(this);
        mMultiCalendarView.setOnTranslationYListener(this);
        mMultiCalendarView.setOnViewChangeListener(this);

        mCalendarView.setRange(2022, 1, 1,

                mCalendarView.getCurYear() + 1, mCalendarView.getCurMonth(), mCalendarView.getCurDay()

        );

        mMultiCalendarView.setRange(2022, 1, 1,
                mMultiCalendarView.getCurYear() + 1, mMultiCalendarView.getCurMonth(), mMultiCalendarView.getCurDay()

        );

        mCalendarView.scrollToCurrent();
        mMultiCalendarView.scrollToCurrent();

        mCalendar = mCalendarView.getDelegate().mCurrentDate;
        setTitle();

        mCalendarLayout.reset(mCalendarView);
    }

    @Override
    protected void initData() {

        int year = mCalendarView.getCurYear();
        int month = mCalendarView.getCurMonth();
        Map<String, Calendar> map = new HashMap<>();
        map.put(getSchemeCalendar(year, month, 3, 0xFF40db25, "假").toString(),
                getSchemeCalendar(year, month, 3, 0xFF40db25, "假"));
        map.put(getSchemeCalendar(year, month, 6, 0xFFe69138, "事").toString(),
                getSchemeCalendar(year, month, 6, 0xFFe69138, "事"));
        map.put(getSchemeCalendar(year, month, 9, 0xFFdf1356, "议").toString(),
                getSchemeCalendar(year, month, 9, 0xFFdf1356, "议"));
        map.put(getSchemeCalendar(year, month, 13, 0xFFedc56d, "记").toString(),
                getSchemeCalendar(year, month, 13, 0xFFedc56d, "记"));
        map.put(getSchemeCalendar(year, month, 14, 0xFFedc56d, "记").toString(),
                getSchemeCalendar(year, month, 14, 0xFFedc56d, "记"));
        map.put(getSchemeCalendar(year, month, 15, 0xFFaacc44, "假").toString(),
                getSchemeCalendar(year, month, 15, 0xFFaacc44, "假"));
        map.put(getSchemeCalendar(year, month, 18, 0xFFbc13f0, "记").toString(),
                getSchemeCalendar(year, month, 18, 0xFFbc13f0, "记"));
        map.put(getSchemeCalendar(year, month, 25, 0xFF13acf0, "假").toString(),
                getSchemeCalendar(year, month, 25, 0xFF13acf0, "假"));
        map.put(getSchemeCalendar(year, month, 27, 0xFF13acf0, "多").toString(),
                getSchemeCalendar(year, month, 27, 0xFF13acf0, "多"));
        //此方法在巨大的数据量上不影响遍历性能，推荐使用
        mCalendarView.setSchemeDate(map);

    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        calendar.addScheme(new Calendar.Scheme());
        calendar.addScheme(0xFF008800, "假");
        calendar.addScheme(0xFF008800, "节");
        return calendar;
    }

    @Override
    public void onYearChange(int year) {

    }

    @Override
    public void onMonthChange(int year, int month) {
        mCalendar = new Calendar();
        mCalendar.setYear(year);
        mCalendar.setMonth(month);
        setTitle();
    }

    @Override
    public void onWeekChange(List<Calendar> weekCalendars) {
        if (weekCalendars != null && weekCalendars.size() > 0) {
            boolean allnot = true;
            Calendar c = null;
            for (Calendar calendar : weekCalendars) {
                if (mCalendar != null) {
                    if (mCalendar.getMonth() == calendar.getMonth()) {
                        allnot = false;
                        break;
                    }
                    else {
                        c = calendar;
                    }
                }
            }
            if (allnot && c != null) {
                mCalendar.setYear(c.getYear());
                mCalendar.setMonth(c.getMonth());
                setTitle();
            }
        }
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        mCalendar = calendar;
        setTitle();
    }

    @Override
    public void onCalendarLongClickOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarLongClick(Calendar calendar) {

    }

    @Override
    public void onViewChange(boolean isMonthView) {
        // 只要变换一次view，下一次又可以重新scrollToSelectCalendar
        toScroll = true;
        this.isMonthView = isMonthView;
    }

    @Override
    public void onYearViewChange(boolean isClose) {

    }

    @Override
    public boolean onCalendarIntercept(Calendar calendar) {
        return false;
    }

    @Override
    public void onCalendarInterceptClick(Calendar calendar, boolean isClick) {

    }

    @Override
    public void onTranslationY(float percent) {
        if (toScroll) {
            toScroll = false;
            mCalendarView.scrollToSelectCalendar();
        }
    }

    private void setTitle() {
        if (mCalendar != null) {
            mTextMonthDay.setText(mCalendar.getYear() + "年" + mCalendar.getMonth() + "月");
        }
    }
}
