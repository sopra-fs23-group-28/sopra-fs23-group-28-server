package ch.uzh.ifi.hase.soprafs23.rest.mapper;

import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.Player;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.rest.dto.LobbyGetDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserPostDTO;
import org.mapstruct.*;
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

  @Mapping(source = "username", target = "username")
  @Mapping(source = "token", target = "token")
  User convertUserPostDTOtoUserEntity(UserPostDTO userPostDTO);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "camelColor", target = "camelColor")
  @Mapping(source = "username", target = "username")
  @Mapping(source = "token", target = "token")
  UserGetDTO convertUserEntityToUserGetDTO(User user);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "creatorId", target = "creatorId")
  @Mapping(source = "roundNumber", target = "roundNumber")
  @Mapping(source = "maxSteps", target = "maxSteps")
  @Mapping(source = "playerIds", target = "playerIds")
  LobbyGetDTO convertLobbyEntityToLobbyGetDTO(Lobby lobby);

  // because the playerIds in the Player entity consist of a List of players, its mapping needs to be defined separately.
  default List<Long> mapPlayerIds(List<Player> players) {
      return players.stream()
              .map(Player::getPlayerId)
              .collect(Collectors.toList());
  }

}