package com.example.almasoft.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.almasoft.model.Movement;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MovementRepository {
    private MovementDao movementDao;
    private LiveData<List<Movement>> allMovements;
    private ExecutorService executorService;

    public MovementRepository(Application application){
        database db = database.getInstance(application);
        movementDao = db.movementDao();
        allMovements = movementDao.getAllMovement();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(Movement movement) {
        executorService.execute(() -> movementDao.insert(movement));
    }

    public void update(Movement movement){
        movementDao.update(movement);
    }

    public void delete(Movement movement){movementDao.delete(movement);}

    public LiveData<List<Movement>> getAllMovements(){
        return allMovements;
    }

}
