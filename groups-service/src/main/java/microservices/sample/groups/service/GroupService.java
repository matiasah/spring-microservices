package microservices.sample.groups.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import microservices.sample.groups.model.Group;
import microservices.sample.groups.repository.GroupRepository;

/**
 * Group service.
 * 
 * @author Matías Hermosilla
 * @since 06-09-2021
 */
@Service
public class GroupService {
    
    /**
     * Group repository.
     */
    @Autowired
    private GroupRepository groupRepository;

    /**
     * Saves a group.
     * 
     * @param group Group to save.
     */
    public void save(Group group) {
        // Save object to database
        this.groupRepository.save(group);
    }

    /**
     * Deletes a group.
     * 
     * @param group Group to delete.
     */
    public void delete(Group group) {
        // Delete object from database
        this.groupRepository.delete(group);
    }

}
