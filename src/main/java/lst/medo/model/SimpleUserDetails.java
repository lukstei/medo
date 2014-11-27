package lst.medo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SimpleUserDetails implements UserDetails {
    String mUsername;
    String mPassword;
    boolean mEnabled;

    List<GrantedAuthority> mGrantedAuthorities = new ArrayList<>();

    public SimpleUserDetails(String username) {
        mUsername = username;
    }

    public SimpleUserDetails(String username, String password, boolean enabled) {
        mUsername = username;
        mPassword = password;
        mEnabled = enabled;
    }

    @Override public Collection<GrantedAuthority> getAuthorities() {
        return mGrantedAuthorities;
    }

    @Override public String getPassword() {
        return mPassword;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    @Override public String getUsername() {
        return mUsername;
    }

    @Override public boolean isAccountNonExpired() {
        return true;
    }

    @Override public boolean isAccountNonLocked() {
        return true;
    }

    @Override public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override public boolean isEnabled() {
        return mEnabled;
    }

    public void setEnabled(boolean enabled) {
        mEnabled = enabled;
    }
}