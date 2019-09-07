package me.longcb.backendassignment.configuration.auth.filter

import me.longcb.backendassignment.configuration.auth.CurrentUserDetails
import me.longcb.backendassignment.service.JwtService
import me.longcb.backendassignment.service.UserService
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import java.text.ParseException
import javax.servlet.http.HttpServletResponse
import org.springframework.web.context.support.WebApplicationContextUtils

class JwtAuthenticationTokenFilter : GenericFilterBean() {
    private var jwtService: JwtService? = null
    private var userService: UserService? = null

    override fun doFilter(req: ServletRequest?, res: ServletResponse?, chain: FilterChain?) {
        lazyInitService(req)
        val authToken = (req as HttpServletRequest).getHeader(TOKEN_HEADER)
        try {
            if (!jwtService!!.validateLoginToken(authToken)) {
                responseAuthError(res); return
            }
            val id = jwtService!!.getUserIdFromLoginToken(authToken)
            val user = userService!!.getUserById(id)
            user?.let {
                val userDetail = CurrentUserDetails(user)
                SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(
                        userDetail, null, userDetail.authorities
                )
            }
            chain?.doFilter(req, res)
        } catch (e: ParseException) {
            responseAuthError(res)
        }
    }

    private fun lazyInitService(request: ServletRequest?) {
        if (request == null) return
        val webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(request.servletContext)
        if (jwtService == null) jwtService = webApplicationContext?.getBean(JwtService::class.java)
        if (userService == null) userService = webApplicationContext?.getBean(UserService::class.java)
    }

    private fun responseAuthError(res: ServletResponse?) {
        (res as? HttpServletResponse)?.sendError(HttpServletResponse.SC_UNAUTHORIZED, UNAUTHORIZED_ERROR)
    }

    companion object {
        const val TOKEN_HEADER = "X-Authorization"
        const val UNAUTHORIZED_ERROR = "The token is not valid."
    }
}
