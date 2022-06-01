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
        CalendarView.OnYearViewChangeListener {

    TextView mTextMonthDay;

    TextView mTextYear;

    TextView mTextLunar;

    TextView mTextCurrentDay;

    CalendarView mCalendarView;

    RelativeLayout mRelativeTool;
    private int mYear;
    CalendarLayout mCalendarLayout;

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
        mTextYear = findViewById(R.id.tv_year);
        mTextLunar = findViewById(R.id.tv_lunar);

        mRelativeTool = findViewById(R.id.rl_tool);
        mCalendarView = findViewById(R.id.calendarView);

        //垂直滚动
        mCalendarView.getMonthViewPager().setOrientation(LinearLayout.VERTICAL);

        //mCalendarView.setRange(2018, 7, 1, 2019, 4, 28);
        mTextCurrentDay = findViewById(R.id.tv_current_day);
        mTextMonthDay.setOnClickListener(v -> {
            if (!mCalendarLayout.isExpand()) {
                mCalendarLayout.expand();
                return;
            }
            mCalendarView.showYearSelectLayout(mYear);
            mTextLunar.setVisibility(View.GONE);
            mTextYear.setVisibility(View.GONE);
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

        //设置日期拦截事件，仅适用单选模式，当前无效
        mCalendarView.setOnCalendarInterceptListener(this);

        mCalendarView.setOnViewChangeListener(this);
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mYear = mCalendarView.getCurYear();
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mTextLunar.setText("今日");
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));
    }

    @Override
    protected void initData() {
        final int year = mCalendarView.getCurYear();
        final int month = mCalendarView.getCurMonth();

        Map<String, Calendar> map = new HashMap<>();
        for (int y = 1997; y < 2082; y++) {
            for (int m = 1; m <= 12; m++) {

                map.put(getSchemeCalendar(y, m, 1, 0xFF40db25, "假").toString(),
                        getSchemeCalendar(y, m, 1, 0xFF40db25, "假"));

                map.put(getSchemeCalendar(y, m, 2, 0xFFe69138, "游").toString(),
                        getSchemeCalendar(y, m, 2, 0xFFe69138, "游"));
                map.put(getSchemeCalendar(y, m, 3, 0xFFdf1356, "事").toString(),
                        getSchemeCalendar(y, m, 3, 0xFFdf1356, "事"));
                map.put(getSchemeCalendar(y, m, 4, 0xFFaacc44, "车").toString(),
                        getSchemeCalendar(y, m, 4, 0xFFaacc44, "车"));
                map.put(getSchemeCalendar(y, m, 5, 0xFFbc13f0, "驾").toString(),
                        getSchemeCalendar(y, m, 5, 0xFFbc13f0, "驾"));
                map.put(getSchemeCalendar(y, m, 6, 0xFF542261, "记").toString(),
                        getSchemeCalendar(y, m, 6, 0xFF542261, "记"));
                map.put(getSchemeCalendar(y, m, 7, 0xFF4a4bd2, "会").toString(),
                        getSchemeCalendar(y, m, 7, 0xFF4a4bd2, "会"));
                map.put(getSchemeCalendar(y, m, 8, 0xFFe69138, "车").toString(),
                        getSchemeCalendar(y, m, 8, 0xFFe69138, "车"));
                map.put(getSchemeCalendar(y, m, 9, 0xFF542261, "考").toString(),
                        getSchemeCalendar(y, m, 9, 0xFF542261, "考"));
                map.put(getSchemeCalendar(y, m, 10, 0xFF87af5a, "记").toString(),
                        getSchemeCalendar(y, m, 10, 0xFF87af5a, "记"));
                map.put(getSchemeCalendar(y, m, 11, 0xFF40db25, "会").toString(),
                        getSchemeCalendar(y, m, 11, 0xFF40db25, "会"));
                map.put(getSchemeCalendar(y, m, 12, 0xFFcda1af, "游").toString(),
                        getSchemeCalendar(y, m, 12, 0xFFcda1af, "游"));
                map.put(getSchemeCalendar(y, m, 13, 0xFF95af1a, "事").toString(),
                        getSchemeCalendar(y, m, 13, 0xFF95af1a, "事"));
                map.put(getSchemeCalendar(y, m, 14, 0xFF33aadd, "学").toString(),
                        getSchemeCalendar(y, m, 14, 0xFF33aadd, "学"));
                map.put(getSchemeCalendar(y, m, 15, 0xFF1aff1a, "码").toString(),
                        getSchemeCalendar(y, m, 15, 0xFF1aff1a, "码"));
                map.put(getSchemeCalendar(y, m, 16, 0xFF22acaf, "驾").toString(),
                        getSchemeCalendar(y, m, 16, 0xFF22acaf, "驾"));
                map.put(getSchemeCalendar(y, m, 17, 0xFF99a6fa, "校").toString(),
                        getSchemeCalendar(y, m, 17, 0xFF99a6fa, "校"));
                map.put(getSchemeCalendar(y, m, 18, 0xFFe69138, "车").toString(),
                        getSchemeCalendar(y, m, 18, 0xFFe69138, "车"));
                map.put(getSchemeCalendar(y, m, 19, 0xFF40db25, "码").toString(),
                        getSchemeCalendar(y, m, 19, 0xFF40db25, "码"));
                map.put(getSchemeCalendar(y, m, 20, 0xFFe69138, "火").toString(),
                        getSchemeCalendar(y, m, 20, 0xFFe69138, "火"));
                map.put(getSchemeCalendar(y, m, 21, 0xFF40db25, "假").toString(),
                        getSchemeCalendar(y, m, 21, 0xFF40db25, "假"));
                map.put(getSchemeCalendar(y, m, 22, 0xFF99a6fa, "记").toString(),
                        getSchemeCalendar(y, m, 22, 0xFF99a6fa, "记"));
                map.put(getSchemeCalendar(y, m, 23, 0xFF33aadd, "假").toString(),
                        getSchemeCalendar(y, m, 23, 0xFF33aadd, "假"));
                map.put(getSchemeCalendar(y, m, 24, 0xFF40db25, "校").toString(),
                        getSchemeCalendar(y, m, 24, 0xFF40db25, "校"));
                map.put(getSchemeCalendar(y, m, 25, 0xFF1aff1a, "假").toString(),
                        getSchemeCalendar(y, m, 25, 0xFF1aff1a, "假"));
                map.put(getSchemeCalendar(y, m, 26, 0xFF40db25, "议").toString(),
                        getSchemeCalendar(y, m, 26, 0xFF40db25, "议"));
                map.put(getSchemeCalendar(y, m, 27, 0xFF95af1a, "假").toString(),
                        getSchemeCalendar(y, m, 27, 0xFF95af1a, "假"));
                map.put(getSchemeCalendar(y, m, 28, 0xFF40db25, "码").toString(),
                        getSchemeCalendar(y, m, 28, 0xFF40db25, "码"));
            }
        }

        //28560 数据量增长不会影响UI响应速度，请使用这个API替换
        mCalendarView.setSchemeDate(map);

        //可自行测试性能差距
        //mCalendarView.setSchemeDate(schemes);
    }

    @Override
    public void onYearChange(int year) {

    }

    @Override
    public void onMonthChange(int year, int month) {

    }

    @Override
    public void onWeekChange(List<Calendar> weekCalendars) {

    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {

    }

    @Override
    public void onCalendarLongClickOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarLongClick(Calendar calendar) {

    }

    @Override
    public void onViewChange(boolean isMonthView) {

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

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }
}
