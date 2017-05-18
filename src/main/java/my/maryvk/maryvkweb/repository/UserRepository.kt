package my.maryvk.maryvkweb.repository

import my.maryvk.maryvkweb.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Int>