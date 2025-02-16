package com.project;

import io.micronaut.core.optim.StaticOptimizations;
import io.micronaut.core.util.EnvironmentProperties;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnvironmentPropertiesOptimizationLoader implements StaticOptimizations.Loader<EnvironmentProperties> {
  private void load0(Map<String, List<String>> env) {
    env.put("PATH", Arrays.asList("path"));
    env.put("MANPATH", Arrays.asList("manpath"));
    env.put("TERM", Arrays.asList("term"));
    env.put("HOMEBREW_PREFIX", Arrays.asList("homebrew.prefix", "homebrew-prefix"));
    env.put("COMMAND_MODE", Arrays.asList("command.mode", "command-mode"));
    env.put("SDKMAN_DIR", Arrays.asList("sdkman.dir", "sdkman-dir"));
    env.put("LOGNAME", Arrays.asList("logname"));
    env.put("HOMEBREW_REPOSITORY", Arrays.asList("homebrew.repository", "homebrew-repository"));
    env.put("PWD", Arrays.asList("pwd"));
    env.put("XPC_SERVICE_NAME", Arrays.asList("xpc.service.name", "xpc.service-name", "xpc-service.name", "xpc-service-name"));
    env.put("INFOPATH", Arrays.asList("infopath"));
    env.put("SDKMAN_CANDIDATES_DIR", Arrays.asList("sdkman.candidates.dir", "sdkman.candidates-dir", "sdkman-candidates.dir", "sdkman-candidates-dir"));
    env.put("__CFBundleIdentifier", Arrays.asList("..cfbundleidentifier", ".-cfbundleidentifier", "-.cfbundleidentifier", "--cfbundleidentifier"));
    env.put("SHELL", Arrays.asList("shell"));
    env.put("SDKMAN_CANDIDATES_API", Arrays.asList("sdkman.candidates.api", "sdkman.candidates-api", "sdkman-candidates.api", "sdkman-candidates-api"));
    env.put("HOMEBREW_CELLAR", Arrays.asList("homebrew.cellar", "homebrew-cellar"));
    env.put("SDKMAN_PLATFORM", Arrays.asList("sdkman.platform", "sdkman-platform"));
    env.put("USER", Arrays.asList("user"));
    env.put("TERMINAL_EMULATOR", Arrays.asList("terminal.emulator", "terminal-emulator"));
    env.put("MICRONAUT_HOME", Arrays.asList("micronaut.home", "micronaut-home"));
    env.put("TMPDIR", Arrays.asList("tmpdir"));
    env.put("SSH_AUTH_SOCK", Arrays.asList("ssh.auth.sock", "ssh.auth-sock", "ssh-auth.sock", "ssh-auth-sock"));
    env.put("XPC_FLAGS", Arrays.asList("xpc.flags", "xpc-flags"));
    env.put("__CF_USER_TEXT_ENCODING", Arrays.asList("..cf.user.text.encoding", "..cf.user.text-encoding", "..cf.user-text.encoding", "..cf.user-text-encoding", "..cf-user.text.encoding", "..cf-user.text-encoding", "..cf-user-text.encoding", "..cf-user-text-encoding", ".-cf.user.text.encoding", ".-cf.user.text-encoding", ".-cf.user-text.encoding", ".-cf.user-text-encoding", ".-cf-user.text.encoding", ".-cf-user.text-encoding", ".-cf-user-text.encoding", ".-cf-user-text-encoding", "-.cf.user.text.encoding", "-.cf.user.text-encoding", "-.cf.user-text.encoding", "-.cf.user-text-encoding", "-.cf-user.text.encoding", "-.cf-user.text-encoding", "-.cf-user-text.encoding", "-.cf-user-text-encoding", "--cf.user.text.encoding", "--cf.user.text-encoding", "--cf.user-text.encoding", "--cf.user-text-encoding", "--cf-user.text.encoding", "--cf-user.text-encoding", "--cf-user-text.encoding", "--cf-user-text-encoding"));
    env.put("LC_CTYPE", Arrays.asList("lc.ctype", "lc-ctype"));
    env.put("HOME", Arrays.asList("home"));
    env.put("SHLVL", Arrays.asList("shlvl"));
  }

  @Override
  public EnvironmentProperties load() {
    Map<String, List<String>> env = new HashMap<String, List<String>>();
    load0(env);
    return EnvironmentProperties.of(env);
  }
}
