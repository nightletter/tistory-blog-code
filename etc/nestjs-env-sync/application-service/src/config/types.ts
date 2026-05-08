export interface ConfigServerPropertySource {
  source: Record<string, unknown>;
}

export interface ConfigServerResponse {
  propertySources?: ConfigServerPropertySource[];
}
