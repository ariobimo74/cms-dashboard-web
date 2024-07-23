package id.co.softwaredeveloperstoday.cms.dashboard.web.service.impl;

import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.RequestRecommendedUsernameDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.dto.ResponseCommonEnumDto;
import id.co.softwaredeveloperstoday.cms.dashboard.web.model.entity.User;
import id.co.softwaredeveloperstoday.cms.dashboard.web.service.CommonValueService;
import id.co.softwaredeveloperstoday.cms.dashboard.web.service.UserService;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.enumeration.EGenderType;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.enumeration.EMemberLevel;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommonValueServiceImpl implements CommonValueService {

    private final UserService userService;

    @Override
    public List<ResponseCommonEnumDto> getGenders() {
        return Arrays.stream(EGenderType.values()).map(e -> new ResponseCommonEnumDto(e.name(), e.getTranslatedName()))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseCommonEnumDto getDefaultMember(EMemberLevel memberLevel) {
        return Arrays.stream(EMemberLevel.values()).filter(e -> Objects.equals(e, memberLevel)).map(
                e -> new ResponseCommonEnumDto(e.name(), e.name())).findAny().orElse(null);
    }

    @Override
    public List<String> getRecommendedUsername(RequestRecommendedUsernameDto recommendedUsernameDto) {
        List<String> recommendedUsernameList = new ArrayList<>(List.of(
                recommendedUsernameDto.getEmail(),
                recommendedUsernameDto.getIdCardNumber(),
                recommendedUsernameDto.getMobilePhoneNumber()
        )).stream().filter(Strings::isNotBlank).collect(Collectors.toList());
        recommendedUsernameList.addAll(convertNameToRecommended(recommendedUsernameDto.getName(), recommendedUsernameDto.getBirthDate()));
        List<User> users = userService.getUserByUsernameIn(recommendedUsernameList);
        if (CollectionUtils.isEmpty(users)) return recommendedUsernameList.stream().distinct().collect(Collectors.toList());
        else return recommendedUsernameList.stream().filter(
                r -> users.stream().noneMatch(u -> Objects.equals(r, u.getUsername()))
        ).distinct().collect(Collectors.toList());
    }

    private List<String> convertNameToRecommended(String name, Date birthDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        List<String> results = new ArrayList<>();
        String[] nameSplit = name.split(" ");
        String[] dateSplit = dateFormat.format(birthDate).split("-");

        if (nameSplit.length > 1)
            results.addAll(List.of(nameSplit[0] + nameSplit[1], nameSplit[0] + "." + nameSplit[1], nameSplit[0] + "_" + nameSplit[1]));
        else results.add(name);
        results.addAll(
                Stream.of(dateSplit).map(d -> Stream.of(nameSplit).map(n -> n + d).collect(Collectors.toList()))
                        .flatMap(Collection::stream).collect(Collectors.toList())
        );

        return results.stream().map(String::toLowerCase).collect(Collectors.toList());
    }
}
