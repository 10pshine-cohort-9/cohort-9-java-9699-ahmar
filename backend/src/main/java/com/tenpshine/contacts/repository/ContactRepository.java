package com.tenpshine.contacts.repository;
import com.tenpshine.contacts.model.Contact;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
public interface ContactRepository extends JpaRepository<Contact,Long>{
 @Query("select distinct c from Contact c left join c.emails e left join c.phoneNumbers p where c.owner.id=:ownerId and (:q='' or lower(c.firstName) like lower(concat('%',:q,'%')) or lower(c.lastName) like lower(concat('%',:q,'%')) or lower(coalesce(c.title,'')) like lower(concat('%',:q,'%')) or lower(e) like lower(concat('%',:q,'%')) or lower(p) like lower(concat('%',:q,'%')))") Page<Contact> search(@Param("ownerId")Long ownerId,@Param("q")String q,Pageable pageable);
 Optional<Contact> findByIdAndOwnerId(Long id,Long ownerId); long countByOwnerId(Long ownerId);
}
