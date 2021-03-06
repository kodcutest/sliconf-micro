package javaday.istanbul.sliconf.micro.repository;

import javaday.istanbul.sliconf.micro.model.event.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EventRepository extends MongoRepository<Event, String>,
        CrudRepository<Event, String> {

    //@Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND Meta(events).id = $1")
    Event findById(String id);

    List<Event> findByName(String name);

    List<Event> findByNameAndDeleted(String name, Boolean deleted);

    Event findEventByKeyEquals(String key);

    List<Event> findAllByExecutiveUserEquals(String executiveUser);

    //@Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND executiveUser = $1 AND deleted = $2")
    List<Event> findAllByExecutiveUserEqualsAndDeleted(String executiveUser, Boolean deleted);

    List<Event> findAllByExecutiveUserAndDeleted(String executiveUser, Boolean deleted);

    Event findByKeyAndExecutiveUser(String key, String executiveUser);

    List<Event> findByNameAndKeyNot(String name, String key);

    List<Event> findByNameAndKeyNotAndDeleted(String name, String key, Boolean deleted);
}