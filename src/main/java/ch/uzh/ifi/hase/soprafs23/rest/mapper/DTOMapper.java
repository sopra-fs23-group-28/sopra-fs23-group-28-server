package ch.uzh.ifi.hase.soprafs23.rest.mapper;

import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.Round;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.rest.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DTOMapper
 * This class is responsible for generating classes that will automatically
 * transform/map the internal representation
 * of an entity (e.g., the User) to the external/API representation (e.g.,
 * UserGetDTO for getting, UserPostDTO for creating)
 * and vice versa.
 * Additional mappers can be defined for new entities.
 * Always created one mapper for getting information (GET) and one mapper for
 * creating information (POST).
 */
@Mapper
public interface DTOMapper {

  DTOMapper INSTANCE = Mappers.getMapper(DTOMapper.class);
  @Mapping(source = "maxSteps", target = "maxSteps")
  Lobby convertStartPostDTOtoLobbyEntity(startPostDTO startPostDTO);
  @Mapping(source = "token", target = "token")
  User convertStartPostDTOtoUserEntity(startPostDTO startPostDTO);
  @Mapping(source = "username", target = "username")
  @Mapping(source = "token", target = "token")
  User convertUserPostDTOtoUserEntity(UserPostDTO userPostDTO);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "camelColor", target = "camelColor")
  @Mapping(source = "username", target = "username")
  @Mapping(source = "token", target = "token")
  UserGetDTO convertUserEntityToUserGetDTO(User user);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "roundNumber", target = "roundNumber")
  @Mapping(source = "maxSteps", target = "maxSteps")
  @Mapping(source = "userIds", target = "userIds")
  LobbyGetDTO convertLobbyEntityToLobbyGetDTO(Lobby lobby);

  @Mapping(source = "categories", target = "categories")
  @Mapping(source = "chosenCategory", target = "chosenCategory")
  @Mapping(source = "answers", target = "answers")
  @Mapping(source = "currentQuestion", target = "currentQuestion")
  RoundGetDTO convertRoundEntityToRoundGetDTO(Round round);

    // because the playerIds in the User entity consist of a List of users, its mapping needs to be defined separately.
  default List<Long> mapPlayerIds(List<User> users) {
      return users.stream()
              .map(User::getId)
              .collect(Collectors.toList());
  }

}