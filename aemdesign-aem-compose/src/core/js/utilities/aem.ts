/**
 * Determines if the user is looking at the edit mode in AEM author.
 *
 * @return {boolean}
 */
export const isAuthorMode = (): boolean => window.parent.CQ?.wcm
