package com.libaray.firebase

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class FirebaseFunction {

    fun getFcmToken(): String {
        var token = ""
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                //  Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            token = task.result
            // Log.d(TAG, "token  $token")

            // Log and toast
            // Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
        })
        return token

    }

    fun getCloudMessagingAPiToken(): String {
        val token="AAAAFYjv3Ns:APA91bHoxYbFaPFH0Ko34C9OedXfXANk3wfsTMvXL7uqdlgLhfiwdYidqv3zIx53gNQZoJrufVslOUuYjiahgCzjtkZPeAdQ9KoZVd6_XXvctOrlxNeOJwhIynx5xAttYv9LwWPlqDQgJhP33s1o_-aCR6gG5DF4HA\t\n"
        return token
    }

}