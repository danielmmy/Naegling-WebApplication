package br.com.naegling.service;

import java.util.List;

import br.com.naegling.domain.Template;
import br.com.naegling.service.exception.TemplateNotFoundException;



public interface TemplateService {
    /**
     * Creates a new template.
     * @param created   The information of the created template.
     * @return  The created template.
     */
    public Template create(Template created);

    /**
     * Deletes a template.
     * @param templateId  The id of the deleted template.
     * @return  The deleted template.
     * @throws TemplateNotFoundException  if no template is found with the given id.
     */
    public Template delete(Long templateId) throws TemplateNotFoundException;

    /**
     * Finds all templates.
     * @return  A list of templates.
     */
    public List<Template> findAll();

    /**
     * Finds template by id.
     * @param id    The id of the wanted template.
     * @return  The found template. If no template is found, this method returns null.
     */
    public Template findById(Long id);

    /**
     * Updates the information of a template.
     * @param updated   The information of the updated template.
     * @return  The updated template.
     * @throws TemplateNotFoundException  if no template is found with given id.
     */
    public Template update(Template updated) throws TemplateNotFoundException;
    
    /**
     * Finds template by userName
     * @param userName
     * @return
     */
    public Template findByName(String name);

}
