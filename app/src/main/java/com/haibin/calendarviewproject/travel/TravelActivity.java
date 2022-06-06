package com.haibin.calendarviewproject.travel;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
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

    RelativeLayout mRelativeTool;
    private int mYear;
    CalendarLayout mCalendarLayout;

    volatile boolean toScroll = true;

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

        //垂直滚动
        mCalendarView.getMonthViewPager().setOrientation(LinearLayout.VERTICAL);

        //mCalendarView.setRange(2018, 7, 1, 2019, 4, 28);
        mTextMonthDay.setOnClickListener(v -> {
            if (!mCalendarLayout.isExpand()) {
                mCalendarLayout.expand();
                return;
            }
            mCalendarView.showYearSelectLayout(mYear);
            mTextMonthDay.setText(String.valueOf(mYear));
        });
        findViewById(R.id.iv_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        mCalendarView.setRange(2020, 1, 1,

                mCalendarView.getCurYear() + 1, mCalendarView.getCurMonth(), mCalendarView.getCurDay()

        );

        mCalendarView.scrollToCurrent();
        setTitle(mCalendarView.getSelectedCalendar());
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
        Calendar c = new Calendar();
        c.setYear(year);
        c.setMonth(month);
        setTitle(c);
    }

    @Override
    public void onWeekChange(List<Calendar> weekCalendars) {

    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        setTitle(calendar);
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
        System.out.println("##########onViewChange " + isMonthView);
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

    private void setTitle(Calendar calendar) {
        mTextMonthDay.setText(calendar.getYear() + "年" + calendar.getMonth() + "月");
    }
}
