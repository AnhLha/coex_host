package com.upit.coex.host.template.actions;

import java.util.Calendar;

public interface IActionCalendar {
    public static interface CalendarViewListener{
        public void onClickedPrevious(Calendar calendar);
        public void onClickedNext(Calendar calendar);
        public void onClickedItemDay(Calendar calendar);
    }
}
