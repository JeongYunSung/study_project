package com.yunseong.study_project.member.command.domain;

public enum Grade {

    BRONZE("브론즈", 0), SILVER("실버", 10), GOLD("골드", 20), PLATINUM("플래티넘", 35), DIAMOND("다이아몬드", 50)
    , MANAGER("관리자", Integer.MIN_VALUE), ADMIN("총관리자", Integer.MAX_VALUE);

    private String tag;
    private int exp;

    Grade(String tag, int exp) {
        this.tag = tag;
        this.exp = exp;
    }

    public Grade cofirmGrade(int exp) {
        if (exp == Integer.MAX_VALUE) {
            return ADMIN;
        }
        if (exp == Integer.MIN_VALUE) {
            return MANAGER;
        }
        if (exp >= 10 && exp < 20) {
            return SILVER;
        }
        if (exp >= 20 && exp < 35) {
            return GOLD;
        }
        if (exp >= 35 && exp < 50) {
            return PLATINUM;
        }
        if (exp >= 50) {
            return DIAMOND;
        }

        return BRONZE;
    }

    public String getTag() {
        return this.tag;
    }

    public int getNeedExp() {
        return this.exp;
    }
}
