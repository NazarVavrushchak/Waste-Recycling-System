package com.ecowaste.recycling.constant;

public final class ValidationConstant {
    public static final String USERNAME_REGEX = "^(?!.*\\.\\.)(?!.*\\.$)(?!.*\\-\\-)"
            + "(?=[ЄІЇҐЁА-ЯA-Z])"
            + "[ЄІЇҐЁєіїґёА-Яа-яA-Za-z0-9\\s-'’.\\\"]"
            + "{1,30}"
            + "(?<![ЭэЁёъЪЫы])$";
    public static final String USERNAME_MESSAGE =   "Username must start with a capital letter. "
            + "English and Ukrainian letters, digits, spaces, apostrophes (’)" +
            ", hyphens (-), dots (.), and quotation marks (\") are allowed. "
            + "Username can be up to 30 characters long. "
            + "Double hyphens, consecutive dots, and trailing dots are not allowed.";
    public static final String PASSWORD_VALIDATION = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])" +
            "(?=.*[@#$%^&-+=()])(?=\\\\S+$).{8, 20}$";
    public static final String PASSWORD_MESSAGE = "Password must be between 8 and 20 characters long, "
            + "contain at least one uppercase letter, one lowercase letter, one digit, and one special character (@#$%^&-+=()). "
            + "No spaces are allowed.";
}
