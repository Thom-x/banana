package fr.aleclerc.banana.jira.app.utils;

import fr.aleclerc.banana.entities.EStoryType;
import fr.aleclerc.banana.entities.Project;
import fr.aleclerc.banana.entities.Sprint;
import fr.aleclerc.banana.entities.Story;
import fr.aleclerc.banana.jira.api.pojo.Board;
import fr.aleclerc.banana.jira.api.pojo.History;
import fr.aleclerc.banana.jira.api.pojo.Issue;
import fr.aleclerc.banana.jira.api.pojo.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.html.Option;
import java.time.Instant;
import java.util.Comparator;
import java.util.Optional;
import java.util.UUID;

public class JiraApiUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JiraApiUtils.class);

    private JiraApiUtils() {

    }

    public static Project convert(Board p) {
        Project project = new Project();
        project.setId(UUID.randomUUID());
        project.setName(p.getName());
        project.setJiraId(p.getId().toString());
        return project;
    }

    public static Sprint convertWithoutId(fr.aleclerc.banana.jira.api.pojo.Sprint s) {
        Sprint sprint = new Sprint();
        sprint.setName(s.getName());
        sprint.setJiraId(s.getId().toString());
        if (s.getEndDate() != null) {
            sprint.setEnd(s.getEndDate().toInstant());
        }
        if (s.getStartDate() != null) {
            sprint.setStart(s.getStartDate().toInstant());
        }
        sprint.setBoardId(String.valueOf(s.getOriginBoardId()));
        return sprint;
    }


    public static Sprint convert(fr.aleclerc.banana.jira.api.pojo.Sprint s) {
        Sprint sprint = JiraApiUtils.convertWithoutId(s);
        sprint.setId(UUID.randomUUID());
        return sprint;
    }

    private static boolean isItemMatching(Item item, Optional<String> sprintId) {
        boolean isFieldMatching = "Sprint".equals(item.getField());
        boolean isToMatching = sprintId.map(id -> id.equals(item.getTo())).orElse(true);
        return isFieldMatching && isToMatching;

    }

    public static Story convertWithoutId(Issue i, Optional<String> sprintId) {
        Story story = new Story();
        story.setName(i.getKey());
        story.setJiraId(i.getId());
        if (i.getFields() != null) {
            Optional<History> historyOptional = Optional.ofNullable(i.getChangelog())
                    .flatMap(changelog -> changelog.getHistories()
                            .stream()
                            .filter(history -> history.getItems().stream().anyMatch(item -> isItemMatching(item, sprintId)))
                            .sorted(Comparator.comparing(History::getCreated))
                            .reduce((first, second) -> second)
                    );
            historyOptional.ifPresent(history -> story.setAddDate(history.getCreated().toInstant()));

            if (i.getFields().getResolutiondate() != null) {
                story.setCloseDate(i.getFields().getResolutiondate().toInstant());
            }
            String sp = i.getFields().getCustomFields().get("SP");
            if (sp != null && !"null".equals(sp)) {
                try {
                    story.setComplexity(Float.valueOf(sp));
                } catch (NumberFormatException e) {
                    LOGGER.error("Convert Story poinrt fail", e);
                }
            }
            String bv = i.getFields().getCustomFields().get("BV");
            if (bv != null && !"null".equals(bv)) {
                try {
                    story.setBusinessValue(Float.valueOf(i.getFields().getCustomFields().get("BV")));
                } catch (NumberFormatException e) {
                    LOGGER.error("Convert business value fail", e);
                }
            }
            String type = i.getFields().getCustomFields().get("TYPE");
            if (type != null && !"null".equals(type)) {
                try {
                    story.setType(EStoryType.valueOf(type));
                } catch (IllegalArgumentException e) {
                    LOGGER.error("Convert story type fail", e);
                }
            }

        }
        return story;

    }

    public static Story convertWithoutId(Issue i) {
        return convertWithoutId(i, Optional.empty());
    }

    public static Story convert(Issue i, Optional<String> sprintId) {
        Story story = JiraApiUtils.convertWithoutId(i, sprintId);
        story.setId(UUID.randomUUID());
        return story;
    }

    public static Story convert(Issue i) {
        Story story = JiraApiUtils.convertWithoutId(i);
        story.setId(UUID.randomUUID());
        return story;
    }

    private void clearHours(Instant instant){
        instant.
    }
}
