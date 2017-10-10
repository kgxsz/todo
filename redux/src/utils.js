export const validateInputValue = inputValue => {
  let inputValueLength = inputValue.trim().length;
  return inputValue && inputValueLength > 0 && inputValueLength < 256;
};
