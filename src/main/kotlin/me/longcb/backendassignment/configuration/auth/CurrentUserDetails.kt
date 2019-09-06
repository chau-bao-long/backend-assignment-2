package me.longcb.backendassignment.configuration.auth

import me.longcb.backendassignment.entity.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CurrentUserDetails(user: UserEntity) : UserDetails {
    private var userName: String = user.name
    private var password: String = user.encryptedPassword
    private var authorities: HashSet<GrantedAuthority> = HashSet()

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = authorities

    override fun isEnabled() = true

    override fun getUsername() = userName

    override fun isCredentialsNonExpired() = true

    override fun getPassword() = password

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true
}
