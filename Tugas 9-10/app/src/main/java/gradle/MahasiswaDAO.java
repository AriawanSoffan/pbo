package gradle;

import java.util.*;
import gradle.models.Mahasiswa;

public interface MahasiswaDAO {
    void insert(Mahasiswa mahasiswa);

    Mahasiswa selectByNim(String nim);

    List<Mahasiswa> selectAll();

    void update(Mahasiswa mahasiswa);

    void delete(String nim);
}
