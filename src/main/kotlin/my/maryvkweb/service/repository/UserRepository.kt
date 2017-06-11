package my.maryvkweb.service.repository

import my.maryvkweb.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Int>