import webpack from 'webpack'

import {
  environment,

  getProjectConfiguration,

  Project,
} from './config'

// Internal
let project: Project = null

function getHMRConfiguration(): webpack.Entry {
  const additionalEntries = {
    footer: [],
    header: [],
  }

  for (const key of Object.keys(additionalEntries)) {
    const additionalEntryKeys = Object.keys(project.additionalEntries)
      .filter((x) => project.hmr[key].mapToOutput.indexOf(x) !== -1)

    for (const entryKey of additionalEntryKeys) {
      additionalEntries[key] = [
        ...additionalEntries[key],
        ...project.additionalEntries[entryKey],
      ]
    }
  }

  return {
    [project.hmr.footer.outputName]: [
      `./${environment.project}/js/${project.entryFile.js}`,
      `./${environment.project}/scss/${project.entryFile.sass}`,
      ...additionalEntries.footer,
    ],

    [project.hmr.header.outputName]: [
      './hmr/empty.css',
      ...additionalEntries.header,
    ],
  }
}

export default (flagHMR: boolean): webpack.Entry => {
  project = getProjectConfiguration()

  if (flagHMR) {
    return getHMRConfiguration()
  }

  return {
    [project.outputName]: [
      `./${environment.project}/js/${project.entryFile.js}`,
      `./${environment.project}/scss/${project.entryFile.sass}`,
    ],

    ...project.additionalEntries,
  }
}
