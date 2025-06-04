package ru.tusur.prediction.service.core.repository;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.prediction.service.core.model.profile.Profile;

/**
 * Интерфейс для управления объектами {@link Profile}.
 */
@Transactional(readOnly = true)
@RegisterConstructorMapper(Profile.class)
public interface ProfileRepository {}
