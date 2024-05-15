package com.myblog.myblog.Repository;

import com.myblog.myblog.Entity.Portfolio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
public interface portfolioRepository extends JpaRepository<Portfolio,Long> {

    Page<Portfolio> findByTitleContaining(String title, Pageable pageable);

    Portfolio findByMainPortfolio(boolean mainPortfolio);


}
