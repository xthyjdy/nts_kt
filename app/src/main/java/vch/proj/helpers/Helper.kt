package vch.proj.helpers

import android.util.Log

class Helper {
    val TAG = "my_log"
    val prefix = "___"

    companion object {
        fun l(data: Any?) {
            if (null != data) {
                Log.e("my_log", "prefix $data")
            }
        }

        fun l(data: List<Any>) {
            data.forEach {
                Log.e("my_log", it.toString())
            }
        }
    }
}