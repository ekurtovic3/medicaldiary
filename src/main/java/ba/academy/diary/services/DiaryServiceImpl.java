package ba.academy.diary.services;

import ba.academy.diary.dto.DiaryInput;


import java.util.*;
import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class DiaryServiceImpl implements DiaryService {

    List<DiaryInput> myobjects = new ArrayList<>();

    @ConfigProperty(name = "prefix.message")
    String prefix;

    @Override
    public List<DiaryInput> getDiary() {
        return myobjects;
    }


    @Override
    public DiaryInput getDiaryById(int id) {
        return myobjects.stream()
                .filter(object -> id == object.getId())
                .findFirst()
                .orElse(null);

    }

    @Override
    public boolean deleteDiaryById(int id) {
        Iterator<DiaryInput> itr = myobjects.iterator();
        while (itr.hasNext()) {
            DiaryInput object = itr.next();
            if (object.getId() == id) {
                myobjects.remove(object);
                return true;
            }
        }
        return false;
    }

    @Override
    public DiaryInput editDiary(int id,DiaryInput diaryInput) {
            Iterator<DiaryInput> itr = myobjects.iterator();
            while (itr.hasNext()) {
                DiaryInput object = itr.next();
                if (object.getId() == id) {
                    object.setDate(diaryInput.getDate());
                    object.setCount(diaryInput.getCount());
                    object.setDescription(diaryInput.getDescription());
                    object.setMedicines(diaryInput.getMedicines());
                    object.setMedicineType(diaryInput.getMedicineType());
                    return object;
                }
            }
            return null;
        }

    @Override
    public DiaryInput addDiaryInput(DiaryInput input) {
        Random rand = new Random(); //instance of random class
        input.setDate(new Date());
        if (input.getId() == null) {
            input.setId(rand.nextInt());
        }
        myobjects.add(input);
        return input;
    }
}
