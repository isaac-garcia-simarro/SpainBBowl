package com.isgarsi.spanbbowl.utils

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.security.MessageDigest
import java.util.regex.Pattern


fun Activity.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, message, duration).show()

fun Activity.toast(resourceId: Int, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, resourceId, duration).show()

fun Activity.snackBar(message: CharSequence, view: View?,
                      duration: Int = Snackbar.LENGTH_SHORT, action: String? = null,
                      actionEvt: (v: View) -> Unit = {}) {

    if (view != null) {
        val snackBar = Snackbar.make(view, message, duration)
        if (!action.isNullOrEmpty()) {
            snackBar.setAction(action, actionEvt)
        }
        snackBar.show()
    }

}

fun Activity.isValidEmail(email: String) : Boolean{
    val pattern = Patterns.EMAIL_ADDRESS
    return pattern.matcher(email).matches()
}

fun Activity.isValidPassword(password: String) : Boolean{
    //Necesita contener -->    1 Num /  1 Minúscula / 1 Mayúscula / 1 caracter especial / Mínimo 8 caracteres
    val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$"
    val pattern = Pattern.compile(passwordPattern)
    return pattern.matcher(password).matches()
}

fun Activity.isValidConfirmPassword(password: String, confirmPassword: String) : Boolean{
    return password == confirmPassword
}

fun Activity.hideKeyboard(){
    val view = this.currentFocus
    if (view != null) {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Activity.fragmentTransactionNoBackStack(supportFragmentManager: FragmentManager, fragment: Fragment, containerId: Int){
    supportFragmentManager.beginTransaction()
        .replace(containerId, fragment)
        .commit()
}

fun Activity.fragmentTransactionBackStack(supportFragmentManager: FragmentManager, fragment: Fragment, containerId: Int){
    supportFragmentManager.beginTransaction()
        .replace(containerId, fragment)
        .addToBackStack(null)
        .commit()
}

fun Context.copyToClipboard(text: String){
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("ctext", text)
    clipboard.setPrimaryClip(clip)
}

fun ViewGroup.inflate(layoutId: Int) = LayoutInflater.from(context).inflate(layoutId, this, false)!!

/*fun ImageView.loadByURL(url: String) = Picasso.get().load(url).transform(CircleTransform()).into(this)

fun ImageView.loadByFile(path: String) = Picasso.get().load(File(path)).into(this)

fun ImageView.loadByURL(url: String, width: Int, height: Int) = Picasso.get().load(url).resize(width, height).centerCrop().transform(CircleTransform()).into(this)

fun ImageView.loadByResource(resource: Int) =  Picasso.get().load(resource).transform(CircleTransform()).into(this)

fun ImageView.loadInitialsImage(text: String, context: Context){
    Picasso.get()
        .load("https://brokenfortest")
        .resize(50, 50)
        .placeholder(AvatarGenerator.avatarImage(context, 200, AvatarConstants.CIRCLE, text, AvatarConstants.COLOR900))
        .into(this)
}*/

fun EditText.validate(validation: (String) -> Unit){
    this.addTextChangedListener(object: TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            validation(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    })
}

fun String.toSHA256(): String{
    return MessageDigest
        .getInstance("SHA-256")
        .digest(this.toByteArray())
        .fold("", { str, it -> str + "%02x".format(it) })
}

fun Fragment.vibratePhone(context: Context) {
    val buzzer = context.getSystemService<Vibrator>()
    val pattern = longArrayOf(0, 200, 100, 300)

    buzzer?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            buzzer.vibrate(VibrationEffect.createWaveform(pattern, -1))
        } else {
            //deprecated in API 26
            buzzer.vibrate(pattern, -1)
        }
    }
}