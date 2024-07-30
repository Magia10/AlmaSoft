package com.example.almasoft.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.almasoft.model.Movement;
import com.example.almasoft.repository.MovementRepository;
import java.util.List;

public class MovementViewModel extends AndroidViewModel {
    private MovementRepository movementRepository;
    private LiveData<List<Movement>> allMovements;

    public MovementViewModel(@NonNull Application application){
        super(application);
        movementRepository = new MovementRepository(application);
        allMovements = movementRepository.getAllMovements();
    }

    public void insert(Movement movement){
        movementRepository.insert(movement);
    }

    public void update(Movement movement){
        movementRepository.update(movement);
    }

    public void delete(Movement movement){
        movementRepository.delete(movement);
    }

    public LiveData<List<Movement>> getAllMovements(){
        return allMovements;
    }
}
