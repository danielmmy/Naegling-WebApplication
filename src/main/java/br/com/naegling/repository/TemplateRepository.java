package br.com.naegling.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.naegling.domain.Template;

public interface TemplateRepository extends JpaRepository<Template, Long> {

}
