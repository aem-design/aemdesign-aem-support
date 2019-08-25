/**
 * Determines if the user is looking at the edit mode in AEM author.
 *
 * @return {boolean}
 */
export const isAuthorEditMode = (): boolean => {
  return window.CQ || window.parent.CQ
}
