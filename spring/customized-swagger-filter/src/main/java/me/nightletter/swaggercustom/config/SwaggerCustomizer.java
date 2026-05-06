package me.nightletter.swaggercustom.config;

import io.swagger.v3.oas.models.PathItem;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Set;

import static java.util.List.of;

@Component
public class SwaggerCustomizer {

    /**
     * 컨트롤러의 클래스 레벨의 @Tag 로 그루핑
     *
     * @return
     */
    @Bean("internalCustomizerFilter")
    public OpenApiCustomizer internalCustomizerFilter() {
        return openApi -> {
            openApi.getPaths().values().forEach(pathItem ->
                            pathItem.readOperations().forEach(op -> {
//            클래스 레벨의 태그가 가장 마지막 인덱스, 메서드 레벨은 선언한 태그 순서대로 들어감
                                String classLevelTag = op.getTags().get(op.getTags().size() - 1);

                                if (op.getTags() != null) {
                                    op.setTags(of(classLevelTag));
                                }
                            })
            );
        };
    }

    /**
     * 컨트롤러의 @Operation.tag 로 그루핑
     *
     * @return
     */
    @Bean("externalCustomizerFilter")
    public OpenApiCustomizer externalCustomizerFilter() {
        Set<String> tags = SwaggerTag.getTags();

        return openApi -> {
            if (openApi.getTags() != null) {
                openApi.setTags(
                        openApi.getTags().stream()
                                .filter(t -> tags.contains(t.getName().trim()))
                                .toList()
                );
            }

            openApi.getPaths().entrySet().removeIf(entry -> {
                PathItem item = entry.getValue();

                item.readOperations().forEach(op -> {
                    if (op.getTags() != null) {

                        op.getTags().remove(op.getTags().size() - 1);

//                                허용된 태그만 유지
                        op.setTags(
                                op.getTags().stream()
                                        .map(String::trim)
                                        .filter(tags::contains)
                                        .toList()
                        );
                    }
                });

//                태그 없는 Operation 삭제
                item.readOperations().removeIf(op ->
                        op.getTags() == null || op.getTags().isEmpty());

                return item.readOperations().isEmpty();
            });

            openApi.getPaths().values().removeIf(openApiPath ->
                    openApiPath.readOperations()
                            .stream()
                            .anyMatch(op -> op.getTags() != null && op.getTags().isEmpty())
            );
        };
    }
}
