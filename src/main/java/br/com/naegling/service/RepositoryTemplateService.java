package br.com.naegling.service;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.naegling.domain.Template;
import br.com.naegling.repository.TemplateRepository;
import br.com.naegling.service.exception.TemplateNotFoundException;

@Service
public class RepositoryTemplateService implements TemplateService {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryAccountService.class);
    
    @Resource
    private TemplateRepository templateRepository;
    
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
	@Override
	public Template create(Template created) {
		LOGGER.debug("Creating a new template with information " + created);
		Template template=Template.getBuilder(created.getName(), created.getPath(),created.getMd5Sum()).build();
		return templateRepository.save(template);
	}

    @Transactional(rollbackFor=TemplateNotFoundException.class)
	@Override
	public Template delete(Long templateId) throws TemplateNotFoundException {
    	 LOGGER.debug("Deleting template with id: " + templateId);
         
         Template deleted = templateRepository.findOne(templateId);
         
         if (deleted == null) {
             LOGGER.debug("No template found with id: " + templateId);
             throw new TemplateNotFoundException();
         }
         
         templateRepository.delete(deleted);
         return deleted;
	}

    @Transactional(readOnly=true)
	@Override
	public List<Template> findAll() {
    	LOGGER.debug("Finding all templates");
    	return templateRepository.findAll();
    }

    @Transactional(readOnly=true)
	@Override
	public Template findById(Long id) {
        LOGGER.debug("Finding template by id: " + id);
        return templateRepository.findOne(id);
	}

    @Transactional(rollbackFor=TemplateNotFoundException.class)
	@Override
	public Template update(Template updated)
			throws TemplateNotFoundException {
        LOGGER.debug("Updating template with information: " + updated);
        
        Template template = templateRepository.findOne(updated.getId());
        
        if (template == null) {
            LOGGER.debug("No template found with id: " + updated.getId());
            throw new TemplateNotFoundException();
        }
        	template.update(updated.getName(), updated.getPath(), updated.getMd5Sum());

        return template;
	}

    @Transactional(readOnly=true)
	@Override
	public Template findByName(String name) {
        String hql = "select t from template t where t.name=:name";
        TypedQuery<Template> query = entityManager.createQuery(hql, Template.class).setParameter("name",
                name);
        List<Template> templates = query.getResultList();

        return templates.size() == 1 ? templates.get(0) : null;
    }

}
