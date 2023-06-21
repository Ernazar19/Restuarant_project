package peaksoft.repositry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.response.StopListResponse;
import peaksoft.entity.StopList;

import java.time.LocalDate;
import java.util.Optional;


public interface StopListRepository extends JpaRepository<StopList,Long> {
    @Query("select new peaksoft.dto.response.StopListResponse(s.id,s.reason,s.date,s.menuItem.name) from StopList s")
    Page<StopListResponse> findAllStopLists(Pageable pageable);

    @Query("select count (*) from StopList s where s.date = :date and UPPER( s.menuItem.name) like upper(:menuItemName)")
    Integer count (LocalDate date, String menuItemName);

}
