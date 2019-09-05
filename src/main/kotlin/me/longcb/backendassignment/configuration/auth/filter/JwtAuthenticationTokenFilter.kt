package me.longcb.backendassignment.configuration.auth.filter

import JwtService
import me.longcb.backendassignment.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class JwtAuthenticationTokenFilter : UsernamePasswordAuthenticationFilter() {
    companion object {
        const val TOKEN_HEADER = "X-Authorization"
    }

    @Autowired
    private lateinit var jwtService: JwtService

    @Autowired
    private lateinit var userService: UserService

    override fun doFilter(req: ServletRequest?, res: ServletResponse?, chain: FilterChain?) {
        val authToken = (req as HttpServletRequest).getHeader(TOKEN_HEADER)
        if (jwtService.validateLoginToken(authToken)) {
            val name = jwtService.getNameFromLoginToken(authToken)
            val user = userService.getUserByName(name)
        }
        chain?.doFilter(req, res)
    }
}
