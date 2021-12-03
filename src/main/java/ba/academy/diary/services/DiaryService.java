package ba.academy.diary.services;

import ba.academy.diary.dto.DiaryInput;
import java.util.List;

public interface DiaryService {

  List<DiaryInput> getDiary();

  DiaryInput addDiaryInput(DiaryInput input);

}
