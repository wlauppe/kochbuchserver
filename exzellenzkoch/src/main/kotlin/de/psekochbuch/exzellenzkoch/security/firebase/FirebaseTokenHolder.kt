package de.psekochbuch.exzellenzkoch.security.firebase

import com.google.api.client.util.ArrayMap
import com.google.firebase.auth.FirebaseToken
import java.util.ArrayList

/**
 * Holder for Information about an registered user
 */
class FirebaseTokenHolder(private val token: FirebaseToken) {
    /**
     * Returns the email of the user
     */
    val email: String
        get() = token.email

    /**
     * Returns the Issuer for the this token.
     */
    val issuer: String
        get() = token.issuer

    /**
     * Returns the user's display name.
     */
    val name: String
        get() = token.name

    /**
     * Returns the name of the user
     */
    val uid: String
        get() = token.uid

    val googleId: String?
        get() = (((token.claims["firebase"] as ArrayMap<*, *>)["identities"] as ArrayMap<*, *>?)!!["google.com"] as ArrayList<String?>?)!![0]

}