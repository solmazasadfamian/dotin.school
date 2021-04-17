package com.dotin.timeOffRequest.exception;

public class ErrorMessages {
    public final static String OVERLAP_ERROR_CODE = "error.overlap";
    public final static String OVERLAP_ERROR_MESSAGE = "زمان تعیین شده با درخواست های قبلی همپوشانی دارد";
    public final static String CAPACITY_ERROR_CODE = "error.capacity";
    public final static String CAPACITY_ERROR_MESSAGE = "تعداد روزهای مرخصی بیشتر از مانده مرخصی میباشد";
    public final static String SUCCESS_CODE = "success";
    public final static String SUCCESS_MESSAGE = "درخواست با موفقیت ثبت شد";
    public final static String MANAGER_DELETE_CODE = "error.delete";
    public final static String MANAGER_DELETE_MESSAGE = "این کاربر مدیر میباشد برای حذف ابتدا نسبت به تغییر مدیر کارمندان زیرمجموعه اقدام فرمایید جهت مشاهده و ویرایش کارمندان زیرمجموعه روی لینک زیر کلیک کنید";
}
