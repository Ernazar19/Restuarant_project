package peaksoft.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.entity.Cheque;

public interface ChequeRepository extends JpaRepository<Cheque,Long> {
}
