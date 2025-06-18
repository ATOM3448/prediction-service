package ru.tusur.prediction.service.core.service.indicator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tusur.prediction.service.api.data.dto.indicator.IndicatorTypeDto;
import ru.tusur.prediction.service.api.data.dto.indicator.UpdateIndicatorDto;
import ru.tusur.prediction.service.core.error.ErrorCode;
import ru.tusur.prediction.service.core.error.ServiceException;
import ru.tusur.prediction.service.core.model.indicator.Indicator;
import ru.tusur.prediction.service.core.repository.IndicatorRepository;

import java.util.List;

import static ru.tusur.prediction.service.core.error.ErrorMessages.OBJECT_NOT_FOUND_MESSAGE;
import static ru.tusur.prediction.service.util.SecurityUtils.getOrganizationIdFromSecurityContext;
import static ru.tusur.prediction.service.util.SecurityUtils.validateAccessByOrganizationId;

@Slf4j
@Service
@RequiredArgsConstructor
public class IndicatorService {

    private final IndicatorRepository indicatorRepository;

    public List<Indicator> getIndicators() {
        long organizationId = getOrganizationIdFromSecurityContext();

        return indicatorRepository.getIndicatorByOrganizationId(organizationId);
    }


    public Indicator getIndicator(long id) {
        Indicator indicator = indicatorRepository.getIndicatorById(id);

        checkExistenceOfIndicator(indicator);

        return indicator;
    }

    public Indicator getIndicatorByIdNotProtected(long id) {
        return indicatorRepository.getIndicatorById(id);
    }

    public Indicator saveIndicator(UpdateIndicatorDto indicator) {
        long organizationId = getOrganizationIdFromSecurityContext();

        IndicatorTypeDto type = indicator.type();
        String name = indicator.name();
        int maxValue = indicator.maxValue();

        Indicator savedIndicator = indicatorRepository.saveIndicator(organizationId, type, name, maxValue);

        log.info(
                "Данные по индикатору #{} сохранены",
                savedIndicator.id()
        );

        return savedIndicator;
    }


    public Indicator updateIndicator(long indicatorId, UpdateIndicatorDto newIndicator) {
        Indicator oldIndicator = indicatorRepository.getIndicatorById(indicatorId);

        checkExistenceOfIndicator(oldIndicator);

        IndicatorTypeDto type = newIndicator.type();
        String name = newIndicator.name();
        int maxValue = newIndicator.maxValue();

        Indicator savedIndicator = indicatorRepository.updateIndicator(indicatorId, type, name, maxValue);

        log.info(
                "Данные по индикатору #{} обновлены на \"{}\"",
                indicatorId,
                name
        );

        return savedIndicator;
    }

    private void checkExistenceOfIndicator(Indicator indicator) {
        if ((indicator == null) || !validateAccessByOrganizationId(indicator.organizationId())) {
            throw new ServiceException(ErrorCode.OBJECT_NOT_FOUND, OBJECT_NOT_FOUND_MESSAGE);
        }
    }
}
