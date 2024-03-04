package com.n11.userservice.general;

import com.n11.userservice.exception.ItemNotFoundException;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Getter
public abstract class BaseEntityService<E extends BaseEntity, R extends JpaRepository<E, Long>> {

    private final R repository;

    protected BaseEntityService(R repository) {
        this.repository = repository;
    }

    public E save(E entity) {
        BaseAdditionalFields baseAdditionalFields = entity.getBaseAdditionalFields();

        if(baseAdditionalFields == null) {
            baseAdditionalFields = new BaseAdditionalFields();
        }

        LocalDateTime now = LocalDateTime.now();

        if(entity.getId() == null) {
            baseAdditionalFields.setDataCreateDate(now);
        }

        baseAdditionalFields.setDataUpdateDate(now);
        entity.setBaseAdditionalFields(baseAdditionalFields);

        return repository.save(entity);
    }

    public List<E> findAll() {
        return repository.findAll();
    }

    public E findByIdWithControl(Long id) {
        Optional<E> isEExist = repository.findById(id);
        E entity;

        if (isEExist.isPresent()) {
            entity = isEExist.get();
        } else {
            throw new ItemNotFoundException(GeneralErrorMessage.ITEM_NOT_FOUND);
        }

        return entity;
    }

    public Optional<E> findById(Long id){
        return repository.findById(id);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public void delete(E entity){
        repository.delete(entity);
    }
}
