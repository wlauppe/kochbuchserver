package de.psekochbuch.exzellenzkoch.security

import com.google.api.core.ApiFuture
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseToken
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
        var username: String? = null
        var jwtToken: String?

        if (requestTokenHeader != null ) {

            val auth : FirebaseAuth = FirebaseAuth.getInstance()
            var apiDecodedToken: ApiFuture<FirebaseToken> = auth.verifyIdTokenAsync(requestTokenHeader)
            while(!apiDecodedToken.isDone){}

            var decodedToken = apiDecodedToken.get()

            val isvalidate = auth.getUserAsync(decodedToken.uid)

            while(!isvalidate.isDone){}

            val user = isvalidate.get()

            if (decodedToken?.uid != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                var authenticationToken: Authentication = FirebaseAuthentication(decodedToken?.uid, FirebaseTokenHolder(decodedToken), null)
                SecurityContextHolder.getContext().setAuthentication(authenticationToken)
            }
        }
        chain.doFilter(request, response)
    }
}