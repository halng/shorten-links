package com.shortentlinks.app.backend.repositories;

import com.shortentlinks.app.backend.entity.Link;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends JpaRepository<Link, String> {

    Optional<Link> getByOriginLink(String originLink);
}
