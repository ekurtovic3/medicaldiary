package ba.academy.diary.services;

import ba.academy.diary.dto.DiaryInput;
import java.util.List;

public interface DiaryService {

  List<DiaryInput> getDiary();
  DiaryInput getDiaryById(int id);
  DiaryInput addDiaryInput(DiaryInput input);
}
