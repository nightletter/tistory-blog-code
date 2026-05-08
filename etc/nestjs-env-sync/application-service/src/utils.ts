export function requireEnv(name: string): string {
  const value = process.env[name]?.trim();
  if (!value) {
    throw new Error(`Environment variable "${name}" is missing or empty`);
  }
  return value;
}

export function getEnv(name: string, fallback: string): string {
  const value = process.env[name]?.trim();
  return value || fallback;
}
