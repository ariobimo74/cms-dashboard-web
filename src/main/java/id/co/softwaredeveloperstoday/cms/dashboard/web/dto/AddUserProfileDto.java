package id.co.softwaredeveloperstoday.cms.dashboard.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.enumeration.EGenderType;
import id.co.softwaredeveloperstoday.cms.dashboard.web.util.enumeration.EMemberLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddUserProfileDto {

    @NotNull
    @NotBlank
    @NotEmpty
    private String name;
    @NotNull
    @NotBlank
    @NotEmpty
    private String idCardNumber;
    @NotNull
    @NotBlank
    @NotEmpty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    @NotNull
    @NotBlank
    @NotEmpty
    private String placeOfBirth;
    @NotNull
    @NotBlank
    @NotEmpty
    private EGenderType gender;
    @NotNull
    @NotBlank
    @NotEmpty
    private String username;
    @NotNull
    @NotBlank
    @NotEmpty
    private String password;
    @NotNull
    @NotBlank
    @NotEmpty
    private RoleDto roleDto;

    private String mobilePhoneNumber;
    private String email;
    private String photoUrl;
    private String allergy;
    private EMemberLevel memberLevel = EMemberLevel.REGULAR;

}
