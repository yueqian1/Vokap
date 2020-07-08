package com.example.wordlistapp.include;

public class WordTestStatus {

    public static final int STATUS_NOTDEFINED = -1;
    public static final int STATUS_START = 0;
    public static final int STATUS_PASSONE = 1;
    public static final int STATUS_PASS = 2;
    public static final int STATUS_FAILONE = 3;
    public static final int STATUS_FAILTWO = 4;
    public static final int STATUS_FAIL = 5;

    public static int getNextStatus(int currentStatus, boolean isTestPassed) {
        if (isTestPassed) {
            switch (currentStatus) {
                case STATUS_START:
                    return STATUS_PASSONE;
                case STATUS_PASSONE:
                case STATUS_PASS:
                    return STATUS_PASS;
                case STATUS_FAILONE:
                case STATUS_FAILTWO:
                    return STATUS_START;
                case STATUS_FAIL:
                    return STATUS_FAIL;
            }
        } else {
            switch (currentStatus) {
                case STATUS_START:
                case STATUS_PASSONE:
                    return STATUS_FAILONE;
                case STATUS_FAILONE:
                    return STATUS_FAILTWO;
                case STATUS_FAILTWO:
                case STATUS_FAIL:
                    return STATUS_FAIL;
                case STATUS_PASS:
                    return STATUS_PASS;
            }
        }
        return STATUS_NOTDEFINED;
    }

}
