package Authentication.utils
import java.util.regex.Pattern

private val pattern: Pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
        "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")

fun emailValidate(email: String): Boolean {
    return pattern.matcher(email).matches()
}

fun nicknameValidate(nickname: String): Boolean {
    return nickname.isNotEmpty() &&
            nickname.contains(Regex("[A-Z]")) || nickname.contains(Regex("[a-z]")) ||
            nickname.contains(Regex("[0-9]"))
}

fun passwordValidate(password: String): Boolean {
    return password.length >= 8 &&
            password.contains(Regex("[A-Z]")) && password.contains(Regex("[a-z]")) &&
            password.contains(Regex("[0-9]")) && password.contains(Regex("[!@#$%^&*+.,-]")) && !password.contains(" ")
}
