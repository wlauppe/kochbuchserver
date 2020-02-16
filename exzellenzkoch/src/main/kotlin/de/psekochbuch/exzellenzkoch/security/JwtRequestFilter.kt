package de.psekochbuch.exzellenzkoch.security

import com.google.api.core.ApiFuture
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseToken
import com.google.firebase.auth.UserInfo
import de.psekochbuch.exzellenzkoch.infrastructure.UserChecker
import de.psekochbuch.exzellenzkoch.security.firebase.FirebaseAuthentication
import de.psekochbuch.exzellenzkoch.security.firebase.FirebaseTokenHolder
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 *Checks user who call the API and authorized them
 */
@Component
class JwtRequestFilter : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val requestTokenHeader = request.getHeader("Authorization")

        val url = request.method
        request.requestURI

        if (requestTokenHeader != null) {

            val auth: FirebaseAuth = FirebaseAuth.getInstance()
            val apiDecodedToken: ApiFuture<FirebaseToken> = auth.verifyIdTokenAsync(requestTokenHeader)
            while (!apiDecodedToken.isDone) {/*wait*/}

                var decodedToken = apiDecodedToken.get()

                val isvalidate = auth.getUserAsync(decodedToken.uid)

                while (!isvalidate.isDone) {/*wait*/}

                    val user = isvalidate.get()

                    if (decodedToken?.uid != null && SecurityContextHolder.getContext().authentication == null /*&& checkIsAuth(decodedToken, request.requestURI, request.method)*/) {
                        val authenticationToken: Authentication = FirebaseAuthentication(decodedToken.uid, FirebaseTokenHolder(decodedToken), null)
                        SecurityContextHolder.getContext().authentication = authenticationToken
                    }
                }
                chain.doFilter(request, response)
    }

    private fun checkIsAuth(decodedToken:FirebaseToken, uri:String, method:String) : Boolean {
        //Check user
        if(uri.matches(Regex("/api/users/report/*")))    return true
        if(uri.matches(Regex("/api/users/\\w+")) && method == "GET") return true
        if(uri.matches(Regex("/api/users/(\\w+|)")) && method == "POST") return true
        if(uri.matches(Regex("/api/users+"))) return equals(decodedToken.claims["normalUser"])


        //Check admin
        if(uri.matches(Regex("/api/admin*"))) return equals(decodedToken.claims["admin"])

        //Check recipes
        if(uri.matches(Regex("/api/recipes")))
        {
            if(method == "GET") return true
            if(method == "POST") return equals(decodedToken.claims["normalUser"])
        }
        if(uri.matches(Regex("/api/recipes+")) ) {
            if(method == "GET") return true
            if(uri.matches(Regex("/api/recipes/report+"))) return true
            return equals(decodedToken.claims["normalUser"])
        }

        //Check images
        if(uri.matches(Regex("/api/images/*/*"))&& method == "GET") return true
        if(uri.matches(Regex("/api/images*"))) return equals(decodedToken.claims["normalUser"])
        return false
    }
}
