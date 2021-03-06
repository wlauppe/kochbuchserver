package de.psekochbuch.exzellenzkoch.infrastructure.dao

import de.psekochbuch.exzellenzkoch.domain.model.Group
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GroupDao : JpaRepository<Group?, Int?>
{

}