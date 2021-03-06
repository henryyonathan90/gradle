/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.integtests.fixtures.executer

import com.google.common.collect.ImmutableList
import org.gradle.integtests.fixtures.VfsRetentionHelper
import org.gradle.test.fixtures.file.TestDirectoryProvider
import org.gradle.util.GradleVersion

class VfsRetentionGradleExecuter extends DaemonGradleExecuter {

    private boolean firstUse = true

    VfsRetentionGradleExecuter(
        GradleDistribution distribution,
        TestDirectoryProvider testDirectoryProvider,
        GradleVersion gradleVersion,
        IntegrationTestBuildContext buildContext
    ) {
        super(distribution, testDirectoryProvider, gradleVersion, buildContext)
        beforeExecute {
            VfsRetentionHelper.waitForChangesToBePickedUp()
        }
    }

    @Override
    protected List<String> getAllArgs() {
        List<Object> conditionalArgs = firstUse ? [VfsRetentionHelper.dropVfsArgument] : ImmutableList.of()
        firstUse = false
        super.getAllArgs() + ([
            VfsRetentionHelper.enableVfsRetentionArgument,
        ] + conditionalArgs).collect { it.toString() }
    }
}
