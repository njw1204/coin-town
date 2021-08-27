package kr.njw.springstudy2.member.constant;

public enum Grade {
    BASIC("일반회원"),
    VIP("VIP");

    private final String name;

    Grade(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
