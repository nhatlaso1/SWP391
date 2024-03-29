package com.free.swd_392.mapper;

import com.free.swd_392.core.mapper.CreateModelMapper;
import com.free.swd_392.core.mapper.DtoMapper;
import com.free.swd_392.core.mapper.UpdateModelMapper;
import com.free.swd_392.dto.user.UserDetails;
import com.free.swd_392.dto.user.UserInfo;
import com.free.swd_392.dto.user.request.RegisterUserRequest;
import com.free.swd_392.entity.user.UserEntity;
import com.free.swd_392.shared.utils.PhoneUtils;
import com.google.firebase.auth.UserRecord;
import lombok.Setter;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        imports = {PhoneUtils.class}
)
@Setter(onMethod_ = {@Autowired})
public abstract class UserMapper implements
        DtoMapper<UserInfo, UserDetails, UserEntity>,
        CreateModelMapper<UserDetails, UserEntity>,
        UpdateModelMapper<UserDetails, UserEntity> {

    @Mapping(target = "id", source = "uid")
    @Mapping(target = "phone", expression = "java(PhoneUtils.removePhoneNumberIdentifier(userRecord.getPhoneNumber()))")
    @Mapping(target = "name", source = "displayName")
    @Mapping(target = "photo", source = "photoUrl")
    @Mapping(target = "active", expression = "java(!userRecord.isDisabled())")
    public abstract UserEntity fromUserRecordToEntity(UserRecord userRecord);

    public abstract void updateInfoRegisterToUserEntity(@MappingTarget UserEntity userEntity, RegisterUserRequest request);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "name")
    @Mapping(target = "photo")
    @Mapping(target = "gender")
    @Mapping(target = "birthday")
    public abstract void updateConvertToEntityApp(@MappingTarget UserEntity entity, UserDetails details);

    @Override
    @Named("convertToInfoMapper")
    @Mapping(target = "roleKind", source = "role.kind")
    public abstract UserInfo convertToInfo(UserEntity entity);

}
